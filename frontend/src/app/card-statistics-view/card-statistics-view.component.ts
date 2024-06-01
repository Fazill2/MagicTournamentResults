import { Component, OnInit } from '@angular/core';
import { TournamentContentService } from '../service/tournament-content.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SpinnerService } from '../service/spinner.service';

@Component({
  selector: 'app-card-statistics-view',
  templateUrl: './card-statistics-view.component.html',
  styleUrls: ['./card-statistics-view.component.scss']
})
export class CardStatisticsViewComponent implements OnInit {
  cards: Array<any> = [];
  formats = ['Modern', 'Standard', 'Pioneer', 'Pauper', 'Legacy', 'Vintage'];
  timeScopes = [
    { value: 'LAST_7_DAYS', label: 'Last Week' }, 
    { value: 'LAST_30_DAYS', label: 'Last Month' }, 
    { value: 'LAST_90_DAYS', label: 'Last 3 Months' },
    { value: 'LAST_365_DAYS', label: 'Last Year' },
    { value: 'ALL', label: 'All Time' }];
  format: string = 'Modern';
  timeScope: string = 'ALL';
  isSideboard: boolean = false;
  selectedFormat: string = 'Modern';
  selectedTime: any = this.timeScopes[4];

  constructor(private tournamentContentService: TournamentContentService,
    private route: ActivatedRoute,
    private router: Router,
    private spinnerService: SpinnerService
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.spinnerService.show();
      this.format = (params['format']) ? params['format'] : this.format;
      this.timeScope = (params['timeScope']) ? params['timeScope'] : this.timeScope;
      this.isSideboard = (params['isSideboard']) ? params['isSideboard'] : this.isSideboard;
      this.selectedFormat = this.format;
      this.selectedTime = this.timeScopes.find((time) => time.value === this.timeScope);
      this.tournamentContentService.getCardStatistics(this.format, this.timeScope, this.isSideboard).subscribe((data: any) => {
        console.log(data);
        this.cards = data;
        this.spinnerService.hide();
      });
    });
  }

  onFormatChange() {
    this.format = this.selectedFormat;
    this.updateQueryParams();
  }

  onTimeChange() {
    this.timeScope = this.selectedTime.value;
    this.updateQueryParams();
  }

  onSideboardChange() {
    this.isSideboard = !this.isSideboard;
    this.updateQueryParams();
  }

  updateQueryParams() {
    this.spinnerService.show();
    this.tournamentContentService.getCardStatistics(this.format, this.timeScope, this.isSideboard).subscribe((data: any) => {
      console.log(data);
      this.cards = data;
      this.spinnerService.hide();
    });
  }

  navigateToCardDetails(card: any) {
    this.router.navigate(['/card-details', card.card.id]);
  }

}
