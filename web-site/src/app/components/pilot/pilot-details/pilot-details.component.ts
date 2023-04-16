import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { PilotOne } from 'src/app/core/model/pilot.model';
import { PilotService } from 'src/app/core/service/pilot.service';

@Component({
  selector: 'app-pilot-details',
  templateUrl: './pilot-details.component.html',
  styleUrls: ['./pilot-details.component.css']
})
export class PilotDetailsComponent {
  pilot?: PilotOne
  pilotId?: string

  constructor(
    private pilotService: PilotService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.pilotId = params['id']
      this.pilotService.getPilot(this.pilotId!).subscribe((pilot: PilotOne) => {
        this.pilot = pilot;
      })
    });
  }

  onBacktoPilotPage() {
    this.router.navigateByUrl("pilot-component")
  }
}
