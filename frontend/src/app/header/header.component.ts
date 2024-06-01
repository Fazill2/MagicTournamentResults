import { query } from '@angular/animations';
import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  cardStats = [
    { label: 'Modern', query: { format: 'Modern' } },
    { label: 'Standard', query: { format: 'Standard' } },
    { label: 'Pioneer', query: { format: 'Pioneer' } },
    { label: 'Pauper', query: { format: 'Pauper' } },
    { label: 'Legacy', query: { format: 'Legacy' } },
    { label: 'Vintage', query: { format: 'Vintage' } }
  ]
}
