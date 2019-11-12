import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { Doctor, IDoctor } from 'app/shared/model/doctor.model';
import { AccountService } from 'app/core/auth/account.service';
import { DoctorService } from './doctor.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-doctor',
  templateUrl: './doctor.component.html'
})
export class DoctorComponent implements OnInit, OnDestroy {
  doctors: IDoctor[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected doctorService: DoctorService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
    public router: Router
  ) {}

  loadAll() {
    this.doctorService
      .query()
      .pipe(
        filter((res: HttpResponse<IDoctor[]>) => res.ok),
        map((res: HttpResponse<IDoctor[]>) => res.body)
      )
      .subscribe((res: IDoctor[]) => {
        this.doctors = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDoctors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDoctor) {
    return item.id;
  }

  registerChangeInDoctors() {
    this.eventSubscriber = this.eventManager.subscribe('doctorListModification', response => this.loadAll());
  }
}
