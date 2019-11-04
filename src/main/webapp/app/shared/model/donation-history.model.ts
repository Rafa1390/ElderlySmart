import { Moment } from 'moment';
import { IPartner } from 'app/shared/model/partner.model';

export interface IDonationHistory {
  id?: number;
  idDonationHistory?: number;
  phone?: string;
  donationMade?: number;
  date?: Moment;
  partner?: IPartner;
}

export class DonationHistory implements IDonationHistory {
  constructor(
    public id?: number,
    public idDonationHistory?: number,
    public phone?: string,
    public donationMade?: number,
    public date?: Moment,
    public partner?: IPartner
  ) {}
}
