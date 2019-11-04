import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPartner } from 'app/shared/model/partner.model';
import { AccountService } from 'app/core/auth/account.service';
import { PartnerService } from './partner.service';

@Component({
  selector: 'jhi-partner',
  templateUrl: './partner.component.html'
})
export class PartnerComponent implements OnInit, OnDestroy {
  partners: IPartner[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected partnerService: PartnerService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.partnerService
      .query()
      .pipe(
        filter((res: HttpResponse<IPartner[]>) => res.ok),
        map((res: HttpResponse<IPartner[]>) => res.body)
      )
      .subscribe((res: IPartner[]) => {
        this.partners = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPartners();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPartner) {
    return item.id;
  }

  registerChangeInPartners() {
    this.eventSubscriber = this.eventManager.subscribe('partnerListModification', response => this.loadAll());
  }
}
