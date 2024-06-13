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

  getCardStatistics(format: string, timeScope: string, isSideboard: boolean) {
    const queryParams = `?format=${format}&timeScope=${timeScope}&isSideboard=${isSideboard}`;
    return this.http.get('http://localhost:8080/card/getTopCards' + queryParams);
  }

  getTournamentPage(page: number, size: number, format: string) {
    const queryParams = `?page=${page}&size=${size}&format=${format}`;
    return this.http.get('http://localhost:8080/tournament/page' + queryParams);
  }

  getRecentDecksWithCard(cardId: string){
    const queryParams = `?cardId=${cardId}`;
    return this.http.get("http://localhost:8080/deck/getDecksForCard" + queryParams);
  }

  getTop3CardsForFormat(format: string, timeScope: string){
    const queryParams = `?format=${format}&timeScope=${timeScope}`;

    return this.http.get("http://localhost:8080/card/getTop3Cards" + queryParams);
  }

  getAutoCompleteOptions(name: string){
    const queryParams = `?name=${name}`;
    return this.http.get<any[]>("http://localhost:8080/card/getAutoCompleteOptions" + queryParams);
  }
}
