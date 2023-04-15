import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from "@angular/common/http";

import { RaceService } from './core/service/race.service';
import { CarService } from './core/service/car.service';
import { PilotService } from './core/service/pilot.service';

import { AppComponent } from './app.component';
import { CarComponent } from './components/car/car.component';
import { PilotComponent } from './components/pilot/pilot.component';
import { RaceComponent } from './components/race/race.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { RaceUpdateComponent } from './components/race/race-update/race-update.component';
import { RaceAddComponent } from './components/race/race-add/race-add.component';
import { RaceDetailsComponent } from './components/race/race-details/race-details.component';
import { PilotAddComponent } from './components/pilot/pilot-add/pilot-add.component';
import { PilotDetailsComponent } from './components/pilot/pilot-details/pilot-details.component';
import { PilotUpdateComponent } from './components/pilot/pilot-update/pilot-update.component';
import { CarAddComponent } from './components/car/car-add/car-add.component';
import { CarUpdateComponent } from './components/car/car-update/car-update.component';
import { CarDetailsComponent } from './components/car/car-details/car-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  declarations: [
    AppComponent,
    CarComponent,
    PilotComponent,
    RaceComponent,
    MainPageComponent,
    NavbarComponent,
    FooterComponent,
    RaceUpdateComponent,
    RaceAddComponent,
    RaceDetailsComponent,
    PilotAddComponent,
    PilotDetailsComponent,
    PilotUpdateComponent,
    CarAddComponent,
    CarUpdateComponent,
    CarDetailsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      positionClass: 'toast-bottom-center'
    }),
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule
  ],
  providers: [
    RaceService,
    CarService,
    PilotService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
