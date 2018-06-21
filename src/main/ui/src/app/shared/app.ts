import {AppType} from "./appType";
import {ContentType} from "./contentType";

export class App {
  id: number;
  name: string;
  type: AppType;
  contentTypes: ContentType[];
  userId: number;
}
