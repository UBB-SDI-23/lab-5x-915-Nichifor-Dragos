import { NgModule } from "@angular/core";
import { RouterModule, Routes, PreloadAllModules } from "@angular/router";

import { RaceComponent } from "./components/race/race.component";
import { PilotComponent } from "./components/pilot/pilot.component";
import { CarComponent } from "./components/car/car.component";

const routes: Routes = [
{
  path: '',
  children: [
    {
        path: 'race-component',
        component: RaceComponent,
        data: {
            title: "Check out our races",
            breadcrumb: "Check out our races"
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
