import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ErrorResponse} from "./error-response";
import {WebUtils} from "./web-utils";
import {User} from "./user";
import {MatDialog} from "@angular/material";
import {InformationDialogComponent} from "./information_dialog/information-dialog.component";
import {Dialog} from "./dialog";
import {AuthService} from "./auth.service";
import {Router} from "@angular/router";
import {Utils} from "./Utils";
import {Observable} from "rxjs/internal/Observable";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs/internal/observable/throwError";

@Injectable()
export class WebClientService {
  constructor(private http: HttpClient,
              public dialog: MatDialog,
              private authService: AuthService,
              private router: Router) {
  }

  login(httpBasicAuth: string) {
    let headers = WebUtils.getHeadersWithoutAuthPopup();
    headers = headers.set('Authorization', httpBasicAuth);

    this.http.get<User>('/api/user', {headers: headers}).subscribe(
      user => {
        this.authService.setUser(user);
        this.router.navigate(['/home']).then(() => console.log('navigating home page...'));
      },
      error => {
        this.handleLoginError(error);
      }
    );
  }

  logout() {
    this.http.get('/logout').subscribe(
      () => {
        this.authService.logout();
        this.router.navigate(['/login']).then(() => console.log('navigating login page...'));
      },
      error => {
        this.handleResponseError(error);
      }
    );
  }

  public get<T>(path: string, options?: any): Observable<T | ErrorResponse> {
    let requestOptions = {};
    if (!Utils.isUndefinedOrNull(options)) {
      requestOptions = options;
    }

    requestOptions['headers'] = WebUtils.getHeadersWithoutAuthPopup();

    return this.http.get<T>(path, requestOptions).pipe(
      catchError((error) => {
        return throwError(this.handleResponseError(error));
      })
    );
  }

  openDialog(data: Dialog) {
    this.dialog.open(InformationDialogComponent, {
      height: '40%',
      width: '30%',
      data: data
    });
  }

  handleResponseError(err: HttpErrorResponse): ErrorResponse {
    let errorResponse = new ErrorResponse();

    if (err.error instanceof ErrorEvent) {
      console.log('A client-side error occurred:', err.error);
      errorResponse.message = err.error.message;
    } else {
      console.log('The backend error occurred:', err.error);
      errorResponse.status = err.error.status;
      errorResponse.message = err.error.message;
    }

    let dialog = new Dialog();
    dialog.header = 'Error';

    if (errorResponse.status === 401) {
      dialog.message = "Session has expired. Please login.";
      this.openDialog(dialog);
      this.authService.logout();
      this.router.navigate(['/login']).then(() => console.log('navigating login page...'));
    } else {
      dialog.message = errorResponse.message;
      this.openDialog(dialog);
    }

    return errorResponse;
  }

  handleLoginError(err: HttpErrorResponse) {
    let dialog = new Dialog();
    dialog.header = 'Error';

    if (err.status === 401) {
      dialog.message = "Incompatible E-mail and Password. Please try again.";
      this.openDialog(dialog);
      return;
    }

    if (err.error instanceof ErrorEvent) {
      console.log('A client-side error occurred:', err.error);
      dialog.message = err.error.message;
    } else {
      console.log('The backend error occurred:', err.error);
      dialog.message = err.error.message;
    }
    this.openDialog(dialog);
  }
}
