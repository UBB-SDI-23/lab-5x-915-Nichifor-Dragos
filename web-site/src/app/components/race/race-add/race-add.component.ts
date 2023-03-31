import { Component } from '@angular/core';
import { Race, RaceAdd } from 'src/app/core/model/race.model';

@Component({
  selector: 'app-race-add',
  templateUrl: './race-add.component.html',
  styleUrls: ['./race-add.component.css']
})
export class RaceAddComponent {

  submitted = false;

  model = new RaceAdd('', '', 0, 0, new Date(2023,10,14));

  resetForm() {this.model = new RaceAdd('', '' , 0, 0, new Date(2023,10,14))}

  onSubmit() { 
    this.submitted = true; 
    console.log("Abs")
  }
}