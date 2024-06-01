import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainViewComponent } from './main-view/main-view.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatListModule } from '@angular/material/list';
import { HttpClientModule } from '@angular/common/http';
import { TournamentDetailsComponent } from './tournament-details/tournament-details.component';
import { HeaderComponent } from './header/header.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTableModule} from '@angular/material/table';
import { DeckDetailsComponent } from './deck-details/deck-details.component';
import { CardDetailsComponent } from './card-details/card-details.component';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import { CardStatisticsViewComponent } from './card-statistics-view/card-statistics-view.component';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { SpinnerComponent } from './spinner/spinner.component';
import { TournamentListComponent } from './tournament-list/tournament-list.component';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    MainViewComponent,
    TournamentDetailsComponent,
    HeaderComponent,
    DeckDetailsComponent,
    CardDetailsComponent,
    CardStatisticsViewComponent,
    SpinnerComponent,
    TournamentListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatListModule,
    HttpClientModule,
    MatMenuModule,
    MatButtonModule,
    MatToolbarModule,
    MatTableModule,
    MatCardModule,
    MatGridListModule,
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    ToastrModule.forRoot({positionClass: 'toast-bottom-center'})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
