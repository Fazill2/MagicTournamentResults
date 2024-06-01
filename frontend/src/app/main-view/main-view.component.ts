import { Component, OnInit } from '@angular/core';
import { TournamentContentService } from '../service/tournament-content.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.scss']
})
export class MainViewComponent implements OnInit{
  tournaments: any;

  constructor(private tournamentContentService: TournamentContentService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.tournamentContentService.getLastTournaments().subscribe((data: any) => {
      console.log(data);
      this.tournaments = data;
    });
  }

  viewTournamentDetails(id: number) {
    console.log('viewDetails', id);
    this.router.navigate(['/tournament-details', id]);
  }

}
