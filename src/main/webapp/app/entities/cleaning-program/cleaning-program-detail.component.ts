import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICleaningProgram } from 'app/shared/model/cleaning-program.model';

@Component({
  selector: 'jhi-cleaning-program-detail',
  templateUrl: './cleaning-program-detail.component.html'
})
export class CleaningProgramDetailComponent implements OnInit {
  cleaningProgram: ICleaningProgram;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cleaningProgram }) => {
      this.cleaningProgram = cleaningProgram;
    });
  }

  previousState() {
    window.history.back();
  }
}
