import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CarComponent } from './components/car/car.component';
import { PilotComponent } from './components/pilot/pilot.component';
import { RaceComponent } from './components/race/race.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { HttpClient, HttpClientModule } from "@angular/common/http";
import { RaceService } from './core/service/race.service';
import { CarService } from './core/service/car.service';
import { PilotService } from './core/service/pilot.service';

@NgModule({
  declarations: [
    AppComponent,
    CarComponent,
    PilotComponent,
    RaceComponent,
    MainPageComponent,
    NavbarComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [
    RaceService,
    CarService,
    PilotService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
