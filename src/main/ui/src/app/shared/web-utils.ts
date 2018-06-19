import {HttpHeaders} from "@angular/common/http";

export class WebUtils {
  static getBasicHeaders(): HttpHeaders {
    let headers = new HttpHeaders();

    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Cache-Control', 'no-cache');

    return headers;
  }

  static getHeadersWithoutAuthPopup(): HttpHeaders {
    let headers = WebUtils.getBasicHeaders();

    // Header to prevent browser's default authorisation popup
    headers = headers.set('X-Requested-With', 'XMLHttpRequest');

    return headers;
  }
}
