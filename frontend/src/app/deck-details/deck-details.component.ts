import { Component, OnInit } from '@angular/core';
import { TournamentContentService } from '../service/tournament-content.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MagicUtilsService } from '../service/magic-utils.service';
import { SpinnerService } from '../service/spinner.service';

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
    public magicUtilsService: MagicUtilsService,
    private router: Router,
    private spinnerService: SpinnerService
  ) { }

  deck: any = {};

  categories = {
    "creature": Array<any>(),
    "artifact": Array<any>(),
    "planeswalker": Array<any>(),
    "enchantment": Array<any>(),
    "instant": Array<any>(),
    "sorcery": Array<any>(),
    "battle": Array<any>(),
    "land": Array<any>(),
    "Sideboard": Array<any>(),
  }

  tables = {
    table1: Array<any>(),
    table2: Array<any>(),
  }
  sideboard: Array<any> = [];
  categoryArray: Array<Category> = [];
  imageTop: number = 0;
  imageLeft: number = 0;
  hoveredCard: any = null;


  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const id = Number(routeParams.get('id'));
    this.spinnerService.show();
    this.tournamentContentService.getDeckDetails(id).subscribe((data: any) => {
      console.log(data);
      this.deck = data;
      const length = this.deck.cards.length;
      this.deck.cards.forEach((card: any) => {
        this.categories[card.category as keyof typeof this.categories].push(card);
      });
      this.prepareDeckList(this.categories, length);
      this.spinnerService.hide();
    });
    
  }

  showImage(card: any, event: any): void {
    this.hoveredCard = card;
    // make sure the image is not out of the screen
    if (event.clientX + 20 + 250 > window.innerWidth) {
      this.imageLeft = event.clientX - 250;
    } else {
      this.imageLeft = event.clientX + 20;
    }
    if (event.clientY + 20 + 400 > window.innerHeight) {
      this.imageTop = event.clientY - 400;
    } else {
      this.imageTop = event.clientY + 20;
    }
  }

  hideImage(): void {
    this.hoveredCard = null;
  }

  viewCardDetails(card: any) {
    const id = card.card.id;
    console.log('viewDetails', id);
    this.router.navigate(['/card-details', id]);
  }


  private prepareDeckList(categories: any, length: number) {
    const deckList1 = Array<any>()
    const deckList2 = Array<any>()
    Object.keys(categories).forEach((key) => {
      if (categories[key].length === 0) {
        return;
      }
      if (deckList1.length < length / 2) {
        deckList1.push({categoryName: key});
      } else {
        deckList2.push({categoryName: key});
      }
      categories[key].forEach((card: any) => {
        card.manaCostIcons = this.magicUtilsService.getManaCostIcons(card.card.manaCost);
        if (deckList1.length < length / 2) {
          deckList1.push(card);
        } else {
          deckList2.push(card);
        }
      });
    });    
    this.tables.table1 = deckList1;
    this.tables.table2 = deckList2;
  }
}
