import { Component, OnInit } from '@angular/core';
import { TournamentContentService } from '../service/tournament-content.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tournament-list',
  templateUrl: './tournament-list.component.html',
  styleUrls: ['./tournament-list.component.scss']
})
export class TournamentListComponent implements OnInit{
  formats = ['All', 'Modern', 'Standard', 'Pioneer', 'Pauper', 'Legacy', 'Vintage'];
  selectedFormat: string = 'All';
  tournaments: any;
  currentPage = 0;
  pageSize = 10;
  totalItems = 0;
  displayedColumns: string[] = ['date', 'name', 'players', 'format'];

  constructor(private tournamentContentService: TournamentContentService, private router: Router) { }

  ngOnInit(): void {
    this.getTournaments();
  }

  getTournaments() {
    this.tournamentContentService.getTournamentPage(this.currentPage, this.pageSize, this.selectedFormat).subscribe((data: any) => {
      console.log(data);
      this.tournaments = data.content;
      this.totalItems = data.totalElements;
    });
  }

  onFormatChange() {
    console.log(this.selectedFormat);
    this.getTournaments();
  }

  onPageChange(event: any): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getTournaments();
  }

  viewTournamentDetails(tournament: any) {
    const id = tournament.id;
    console.log('viewDetails', id);
    this.router.navigate(['/tournament-details', id]);
  }
}
