import { query } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, catchError, startWith, switchMap } from 'rxjs';
import { TournamentContentService } from '../service/tournament-content.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  searchControl = new FormControl();
  filteredOptions: Observable<any[]> = new Observable<any[]>();

  cardStats = [
    { label: 'Modern', query: { format: 'Modern' } },
    { label: 'Standard', query: { format: 'Standard' } },
    { label: 'Pioneer', query: { format: 'Pioneer' } },
    { label: 'Pauper', query: { format: 'Pauper' } },
    { label: 'Legacy', query: { format: 'Legacy' } },
    { label: 'Vintage', query: { format: 'Vintage' } }
  ]

  
  ngOnInit() {
    this.filteredOptions = this.searchControl.valueChanges.pipe(
      startWith(''),
      switchMap(value => this.fetchOptions(value))
    );
  }

  constructor(private router: Router, private tournamentContentService: TournamentContentService) { }


  fetchOptions(value: string): Observable<any[]> {
    if (value.length < 3 || value === ' ' || value == "[object Object]") {
      return new Observable<any[]>(observer => observer.next([]));
    }
    return this.tournamentContentService.getAutoCompleteOptions(value).pipe(
      catchError(error => {
        console.error('Error fetching autocomplete options:', error);
        return new Observable<any[]>(observer => observer.next([]));
      })
    );
  }

  onOptionSelected(event: any) {
    if (!event.option && !event.option.value) {
      return;
    }
    const selectedOption = event.option.value;
    console.log(selectedOption);
    const id = selectedOption.id;
    this.searchControl.setValue('');

    if (id) {
      this.router.navigate([`/card-details/${id}`]);
    }
  }

  displayFn(option: any) {
    console.log(option.name);
    return option ? option.name : '';
  }

}
