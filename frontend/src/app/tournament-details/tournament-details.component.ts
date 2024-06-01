import { Component, OnInit } from '@angular/core';
import { TournamentContentService } from '../service/tournament-content.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MagicUtilsService } from '../service/magic-utils.service';

@Component({
  selector: 'app-tournament-details',
  templateUrl: './tournament-details.component.html',
  styleUrls: ['./tournament-details.component.scss']
})
export class TournamentDetailsComponent implements OnInit{
  tournament: any;

  displayedColumns: string[] = ['place', 'player', 'name', 'colors', 'averageCMC'];
  
  constructor(
    private tournamentContentService: TournamentContentService,
    private route: ActivatedRoute,
    private magicUtilsService: MagicUtilsService,
    private router: Router
  ) { }
  
  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const id = Number(routeParams.get('id'));

    this.tournamentContentService.getTournamentDetails(id).subscribe((data: any) => {
      console.log(data);
      this.tournament = data;
    });
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
