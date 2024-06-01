import { Component, OnInit } from '@angular/core';
import { TournamentContentService } from '../service/tournament-content.service';
import { ActivatedRoute } from '@angular/router';
import { MagicUtilsService } from '../service/magic-utils.service';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.scss']
})
export class CardDetailsComponent implements OnInit {
  constructor(
    private tournamentContentService: TournamentContentService,
    private route: ActivatedRoute,
    private magicUtilsService: MagicUtilsService
  ) { }

  card: any;

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const id = routeParams.get('id');
    if (!id) {
      return;
    }

    this.tournamentContentService.getCardDetails(id).subscribe((data: any) => {
      console.log(data);
      this.card = data;
    });
  }
}
