export class Utils {
  static isUndefinedOrNull(value: any): boolean {
    return typeof(value) === 'undefined' || value === null;
  }
}
