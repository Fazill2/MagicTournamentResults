import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MagicUtilsService {
  private colorIcons = {
    "W": '<i class="ms ms-w ms-cost ms-shadow"></i>',
    "U": '<i class="ms ms-u ms-cost ms-shadow"></i>',
    "B": '<i class="ms ms-b ms-cost ms-shadow"></i>',
    "R": '<i class="ms ms-r ms-cost ms-shadow"></i>',
    "G": '<i class="ms ms-g ms-cost ms-shadow"></i>',
    'C': '<i class="ms ms-c ms-cost ms-shadow"></i>'
  };

  constructor() { }

  public getColorIcons(colorString: string): string {
    let color = "";
    if (colorString === undefined || colorString === null || colorString === "") {
      return this.colorIcons['C'];
    }

    for (let i = 0; i < colorString.length; i++) {
      let c: string = colorString.charAt(i);
      if (this.colorIcons[c as keyof typeof this.colorIcons]) {
        color += this.colorIcons[c as keyof typeof this.colorIcons];
      }
    }
    return color;
  }
}
