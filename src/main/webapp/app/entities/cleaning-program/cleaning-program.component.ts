import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICleaningProgram } from 'app/shared/model/cleaning-program.model';
import { AccountService } from 'app/core/auth/account.service';
import { CleaningProgramService } from './cleaning-program.service';

@Component({
  selector: 'jhi-cleaning-program',
  templateUrl: './cleaning-program.component.html'
})
export class CleaningProgramComponent implements OnInit, OnDestroy {
  cleaningPrograms: ICleaningProgram[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected cleaningProgramService: CleaningProgramService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.cleaningProgramService
      .query()
      .pipe(
        filter((res: HttpResponse<ICleaningProgram[]>) => res.ok),
        map((res: HttpResponse<ICleaningProgram[]>) => res.body)
      )
      .subscribe((res: ICleaningProgram[]) => {
        this.cleaningPrograms = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCleaningPrograms();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICleaningProgram) {
    return item.id;
  }

  registerChangeInCleaningPrograms() {
    this.eventSubscriber = this.eventManager.subscribe('cleaningProgramListModification', response => this.loadAll());
  }
}
