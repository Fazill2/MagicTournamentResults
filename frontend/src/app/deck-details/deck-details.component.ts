import { Component, OnInit } from '@angular/core';
import { TournamentContentService } from '../service/tournament-content.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MagicUtilsService } from '../service/magic-utils.service';

interface Category {
  name: string;
  cards: Array<any>
}


@Component({
  selector: 'app-deck-details',
  templateUrl: './deck-details.component.html',
  styleUrls: ['./deck-details.component.scss']
})
export class DeckDetailsComponent implements OnInit {

    
  constructor(
    private tournamentContentService: TournamentContentService,
    private route: ActivatedRoute,
    private magicUtilsService: MagicUtilsService,
    private router: Router
  ) { }

  deck: any;

  categories = {
    "creature": [],
    "artifact": [],
    "land": [],
    "planeswalker": [],
    "enchantment": [],
    "instant": [],
    "sorcery": [],
    "battle": []
  }


  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const id = Number(routeParams.get('id'));

    this.tournamentContentService.getDeckDetails(id).subscribe((data: any) => {
      console.log(data);
      this.deck = data;
      // this.deck.cards.array.forEach(element: any => {
      //   this.categories[element.category as keyof typeof String] = 
      // });
    });
  }

  hoveredCard: any = null;

  showImage(card: any): void {
    this.hoveredCard = card;
  }

  hideImage(): void {
    this.hoveredCard = null;
  }

  viewCardDetails(card: any) {
    const id = card.card.id;
    console.log('viewDetails', id);
    this.router.navigate(['/card-details', id]);
  }

}
