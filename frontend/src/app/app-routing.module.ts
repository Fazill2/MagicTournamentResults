import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainViewComponent } from './views/main-view/main-view.component';
import { TournamentDetailsComponent } from './views/tournament-details/tournament-details.component';
import { DeckDetailsComponent } from './views/deck-details/deck-details.component';
import { CardDetailsComponent } from './views/card-details/card-details.component';
import { CardStatisticsViewComponent } from './views/card-statistics-view/card-statistics-view.component';
import { TournamentListComponent } from './views/tournament-list/tournament-list.component';

const routes: Routes = [
  // Add this line to the routes array
  { path: '', redirectTo: '/main-view', pathMatch: 'full' },
  { path: 'main-view',component: MainViewComponent },
  { path: 'tournament-details/:id', component: TournamentDetailsComponent },
  { path: 'deck-details/:id', component: DeckDetailsComponent },
  { path: 'card-details/:id', component: CardDetailsComponent },
  { path: 'card-stats', component: CardStatisticsViewComponent },
  { path: 'tournament-list', component: TournamentListComponent },
  { path: '**', redirectTo: '/main-view' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
