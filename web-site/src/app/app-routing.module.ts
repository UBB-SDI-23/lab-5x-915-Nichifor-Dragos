import { NgModule } from "@angular/core";
import { RouterModule, Routes, PreloadAllModules } from "@angular/router";

import { MainPageComponent } from "./components/main-page/main-page.component";
import { RaceComponent } from "./components/race/race.component";
import { PilotComponent } from "./components/pilot/pilot.component";
import { CarComponent } from "./components/car/car.component";
import { RaceAddComponent } from "./components/race/race-add/race-add.component";
import { RaceUpdateComponent } from "./components/race/race-update/race-update.component";

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
            title: "Check out our races",
            breadcrumb: "Check out our races"
        }
    },
    {
      path: 'race-component/race-update-component:id',
      component: RaceUpdateComponent,
      data: { 
          title: "Update a car",
          breadcrumb: "Update a car"
      }
    },
    {
      path: 'race-component/race-add-component',
      component: RaceAddComponent,
      data: { 
          title: "Add a car",
          breadcrumb: "Add a car"
      }
    },
    {
        path: 'pilot-component',
        component: PilotComponent,
        data: {
         	title: "Check out our pilots",
          	breadcrumb: "Check out our pilots"
      }
    },
    {
        path: 'car-component',
        component: CarComponent,
        data: { 
          	title: "Check out our pilots",
          	breadcrumb: "Check out our cars"
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
