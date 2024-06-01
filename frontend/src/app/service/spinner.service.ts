import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  private spinnerStatus: boolean = false;

  constructor() { }

  public getSpinnerStatus(): boolean {
    return this.spinnerStatus;
  }

  public show(): void {
    this.spinnerStatus = true;
  }

  public hide(): void {
    this.spinnerStatus = false;
  }
}
