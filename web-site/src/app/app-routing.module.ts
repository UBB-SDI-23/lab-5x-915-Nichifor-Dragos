import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

import { MainPageComponent } from "./components/main-page/main-page.component";
import { RaceComponent } from "./components/race/race.component";
import { PilotComponent } from "./components/pilot/pilot.component";
import { CarComponent } from "./components/car/car.component";
import { RaceAddComponent } from "./components/race/race-add/race-add.component";
import { RaceUpdateComponent } from "./components/race/race-update/race-update.component";
import { RaceDetailsComponent } from "./components/race/race-details/race-details.component";

const routes: Routes = [
{
  path: '',
  children: [
    {
      path:'',
      component: MainPageComponent
    },
    {
        path: 'race-component',
        component: RaceComponent,
        data: {
            title: "Check out the races",
            breadcrumb: "Check out the races"
        }
    },
    {
      path: 'race-add-component',
      component: RaceAddComponent,
      data: { 
          title: "Add a race",
          breadcrumb: "Add a race"
      }
    },
    {
      path: 'race-update-component/:id',
      component: RaceUpdateComponent,
      data: { 
          title: "Update a race",
          breadcrumb: "Update a race"
      }
    },
    {
      path: 'race-details-component/:id',
      component: RaceDetailsComponent,
      data: { 
          title: "See the details of a race",
          breadcrumb: "See the details of a race"
      }
    },
    {
        path: 'pilot-component',
        component: PilotComponent,
        data: {
         	title: "Check out the pilots",
          	breadcrumb: "Check out the pilots"
      }
    },
    {
        path: 'car-component',
        component: CarComponent,
        data: { 
          	title: "Check out the cars",
          	breadcrumb: "Check out the cars"
      }
    }
    ]
}

];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})

export class AppRoutingModule { }
