import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'asylumFilter'
})
export class AsylumFilter implements PipeTransform {
  transform(value: any, searchText: any) {
    if (!value || !searchText) {
      return value;
    }
    const result = [];
    for (const post of value) {
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
