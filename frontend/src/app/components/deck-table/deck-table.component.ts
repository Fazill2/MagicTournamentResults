import { Component, Input } from '@angular/core';
import { MagicUtilsService } from '../../service/magic-utils.service';

@Component({
  selector: 'app-deck-table',
  templateUrl: './deck-table.component.html',
  styleUrls: ['./deck-table.component.scss']
})
export class DeckTableComponent {
  @Input()
  public decks: any;
  @Input()
  public viewDeckDetails: any;
  @Input()
  public getColorIconsForDeck: any;

  displayedColumns: string[] = ['place', 'player', 'name', 'colors', 'averageCMC'];

  constructor(
    public magicUtilsService: MagicUtilsService
  ){
  }
}
