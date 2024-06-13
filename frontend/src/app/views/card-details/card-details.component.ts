import { Component, OnInit } from '@angular/core';
import { TournamentContentService } from '../../service/tournament-content.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { MagicUtilsService } from '../../service/magic-utils.service';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.scss']
})
export class CardDetailsComponent implements OnInit {
  constructor(
    private tournamentContentService: TournamentContentService,
    private route: ActivatedRoute,
    private magicUtilsService: MagicUtilsService,
    private router: Router
  ) { }

  card: any = {};
  decks: any;

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (!id) {
        return;
      }
      this.tournamentContentService.getCardDetails(id).subscribe((data: any) => {
        console.log(data);
        this.card = data;
      });
  
      this.tournamentContentService.getRecentDecksWithCard(id).subscribe((data: any) => {
        console.log(data);
        this.decks = data;
      })
    });

    // this.tournamentContentService.getCardDetails(id).subscribe((data: any) => {
    //   console.log(data);
    //   this.card = data;
    // });

    // this.tournamentContentService.getRecentDecksWithCard(id).subscribe((data: any) => {
    //   console.log(data);
    //   this.decks = data;
    // })
  }

  getColorIconsForDeck(deck: any) {
    return this.magicUtilsService.getColorIcons(deck.color);
  }

  viewDeckDetails(deck: any) {
    const id = deck.id;
    console.log(deck)
    console.log('viewDetails', id);
    this.router.navigate(['/deck-details', id]);
  }
}
