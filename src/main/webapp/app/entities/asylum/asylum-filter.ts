import { Pipe, PipeTransform } from '@angular/core';
import { IAsylum } from 'app/shared/model/asylum.model';

@Pipe({
  name: 'asylumFilter'
})
export class AsylumFilter implements PipeTransform {
  transform(asylums: IAsylum[], searchText: string) {
    if (!asylums || !searchText) {
      return asylums;
    }
    const result = [];
    for (const post of asylums) {
      if (post.name.toLowerCase().indexOf(searchText.toLowerCase()) > -1) {
        result.push(post);
      }

      if (post.identification.toLowerCase().indexOf(searchText.toLowerCase()) > -1) {
        result.push(post);
      }

      if (post.email.toLowerCase().indexOf(searchText.toLowerCase()) > -1) {
        result.push(post);
      }
    }

    return result;
  }
}
