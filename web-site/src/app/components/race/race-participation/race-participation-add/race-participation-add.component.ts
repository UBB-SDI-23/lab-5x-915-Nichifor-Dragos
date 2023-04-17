import { Component } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import {debounceTime, distinctUntilChanged, filter, fromEvent, map, of, Subject} from 'rxjs';
import { Pilot } from 'src/app/core/model/pilot.model';
import { PilotService } from 'src/app/core/service/pilot.service';
import { RaceService } from 'src/app/core/service/race.service';
import { ParticipationAdd } from 'src/app/core/model/race.model';

@Component({
  selector: 'app-race-participation-add',
  templateUrl: './race-participation-add.component.html',
  styleUrls: ['./race-participation-add.component.css']
})
export class RaceParticipationAddComponent {

  startPosition?: string
  needAccommodation?: boolean

  submitted: boolean = false

  raceId?: string
  pilotId?: string

  selectedOption?: string;
  selectedPilot?: Pilot;
  searchTerm = new Subject<string>();
  options?: Pilot[];

  constructor(
    private raceService: RaceService,
    private pilotService: PilotService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.raceId = params['id']
      this.startPosition = params['nextParticipation']
    })

    this.searchTerm.pipe(
      debounceTime(1000)).subscribe(term => {
        if (term.trim()) {
          this.pilotService.getPilotsByName(term).subscribe({
            next: (pilots: Pilot[]) => {
              this.options = pilots; }});
        } else { this.options = undefined; }
    });

  }

  onSubmit() {
    this.submitted = true;
    if (this.needAccommodation && this.startPosition) {
      const participation: ParticipationAdd = {
        needAccommodation: this.needAccommodation,
        startPosition: this.startPosition,
      }
      
      this.pilotId = this.selectedPilot?.id.toString();

      this.raceService.addParticipation(participation, this.pilotId!, this.raceId!).subscribe(
        (response) => { this.toastrService.success("Participation added successfully", '', { progressBar: true }); this.onBackToRaceDetailsPage() },
        (error) => { this.toastrService.error("Could not add participation", '', { progressBar: true }); this.onBackToRaceDetailsPage() })
    } else { this.toastrService.error("Could not add participation", '', { progressBar: true }); this.onBackToRaceDetailsPage() }
  }

  onSelection(event: any): void {
    console.log(event)
    this.selectedOption = event.option.value.firstName + " " + event.option.value.lastName;
    this.selectedPilot = event.option.value;
  }

  search(term: string): void {
    this.searchTerm.next(term);
  }

  onBackToRaceDetailsPage() {
    this.router.navigate(['/race-details-component', this.raceId])
  }


}
