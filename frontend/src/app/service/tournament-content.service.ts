import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TournamentContentService {

  constructor(private http: HttpClient) { }

  getLastTournaments() {
    return this.http.get('http://localhost:8080/tournament/last10');
  }

  getTournamentDetails(id: number) {
    const queryParams = `?id=${id}`;
    return this.http.get('http://localhost:8080/tournament/details' + queryParams);
  }

  getDeckDetails(id: number) {
    const queryParams = `?id=${id}`;
    return this.http.get('http://localhost:8080/deck/details' + queryParams);
  }

  getCardDetails(id: string) {
    const queryParams = `?id=${id}`;
    return this.http.get('http://localhost:8080/card/details' + queryParams);
  }
}
