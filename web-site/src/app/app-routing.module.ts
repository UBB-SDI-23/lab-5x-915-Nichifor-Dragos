import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

import { MainPageComponent } from "./components/main-page/main-page.component";
import { RaceComponent } from "./components/race/race.component";
import { PilotComponent } from "./components/pilot/pilot.component";
import { CarComponent } from "./components/car/car.component";
import { RaceAddComponent } from "./components/race/race-add/race-add.component";
import { RaceUpdateComponent } from "./components/race/race-update/race-update.component";
import { RaceDetailsComponent } from "./components/race/race-details/race-details.component";
import { PilotAddComponent } from "./components/pilot/pilot-add/pilot-add.component";
import { PilotUpdateComponent } from "./components/pilot/pilot-update/pilot-update.component";
import { PilotDetailsComponent } from "./components/pilot/pilot-details/pilot-details.component";
import { CarAddComponent } from "./components/car/car-add/car-add.component";
import { CarUpdateComponent } from "./components/car/car-update/car-update.component";
import { CarDetailsComponent } from "./components/car/car-details/car-details.component";

const routes: Routes = [
{
  path: '',
  children: [
    { path: '', redirectTo: '/home-page', pathMatch: 'full' },
    { path:'home-page', component: MainPageComponent },
    { path: 'race-component', component: RaceComponent,  data: { pageNo: 0, pageSize: 25 } },
    { path: 'race-add-component', component: RaceAddComponent },
    { path: 'race-update-component/:id', component: RaceUpdateComponent },
    { path: 'race-details-component/:id', component: RaceDetailsComponent },
    { path: 'pilot-component', component: PilotComponent, data: { pageNo: 0, pageSize: 25 } },
    { path: 'pilot-add-component', component: PilotAddComponent },
    { path: 'pilot-update-component/:id', component: PilotUpdateComponent },
    { path: 'pilot-details-component/:id', component: PilotDetailsComponent },
    { path: 'car-component', component: CarComponent, data: { pageNo: 0, pageSize: 25 }  },
    { path: 'car-add-component', component: CarAddComponent },
    { path: 'car-update-component/:id', component: CarUpdateComponent },
    { path: 'car-details-component/:id', component: CarDetailsComponent },
  ]
}

];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})

export class AppRoutingModule { }
