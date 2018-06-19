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
        const errorResponse = this.handleResponseError(error);
        let dialog = new Dialog();
        dialog.header = 'Error';
        dialog.message = errorResponse.message;
        this.openDialog(dialog);
      }
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
      errorResponse.status = err.status;
      errorResponse.message = err.message;
    }

    return errorResponse;
  }
}
