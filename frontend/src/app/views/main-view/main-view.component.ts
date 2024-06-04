import { Component, OnInit } from '@angular/core';
import { TournamentContentService } from '../../service/tournament-content.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.scss']
})
export class MainViewComponent implements OnInit{
  tournaments: any;
  cards: any;
  formats = ['Modern', 'Standard', 'Pioneer', 'Pauper', 'Legacy', 'Vintage'];
  format: string = 'Modern';
  selectedFormat: string = 'Modern';
  constructor(private tournamentContentService: TournamentContentService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.tournamentContentService.getLastTournaments().subscribe((data: any) => {
      console.log(data);
      this.tournaments = data;
    });

    this.tournamentContentService.getTop3CardsForFormat('Modern', 'LAST_7_DAYS').subscribe((data: any) => {
      console.log(data);
      this.cards = data;
    });
  }

  viewTournamentDetails(id: number) {
    console.log('viewDetails', id);
    this.router.navigate(['/tournament-details', id]);
  }

  navigateToCardDetails(card: any) {
    console.log('navigateToCardDetails', card);
    this.router.navigate(['/card-details', card.card.id]);
  }

  onFormatChange() {
    this.format = this.selectedFormat;
    this.tournamentContentService.getTop3CardsForFormat(this.format, 'LAST_7_DAYS').subscribe((data: any) => {
      console.log(data);
      this.cards = data;
    });
  }
}
