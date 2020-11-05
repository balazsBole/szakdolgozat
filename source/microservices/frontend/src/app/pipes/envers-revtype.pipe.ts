import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'enversRevtype'
})
export class EnversRevtypePipe implements PipeTransform {

  transform(value: any, ...args: unknown[]): unknown {
    switch (value) {
      case "insert":
        return 'created';
      case "update":
        return 'edited';
      case "delete":
        return 'deleted';
      default:
        return value;
    }
  }

}
