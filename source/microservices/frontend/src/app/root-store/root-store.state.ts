import {IBicStoreState} from './bic-store/bic-store.state';
import {IBusinessPartnerBicTopicStoreState} from './business-partner-bic-topic-store/business-partner-bic-topic-store.state';
import {IBusinessPartnerStoreState} from './business-partner-store/business-partner-store.state';
import {IBusinessPartnerTypeStoreState} from './business-partner-type-store/business-partner-type-store.state';
import {IContactInfoAssignmentStoreState} from './contact-info-assignment/contact-info-assignment-store.state.interface';
import {IContactPersonStoreState} from './contact-person-store/contact-person-store.state.interface';
import {ITaskStoreState} from './task-store/task-store.state.interface';
import {ITopicStoreState} from './topic/topic-store.state.interface';

export interface IRootState {
  bic: IBicStoreState;
  businessPartner: IBusinessPartnerStoreState;
  businessPartnerTypes: IBusinessPartnerTypeStoreState;
  businessPartnerBicTopic: IBusinessPartnerBicTopicStoreState;
  contactInfoAssignment: IContactInfoAssignmentStoreState;
  contactPerson: IContactPersonStoreState;
  task: ITaskStoreState;
  topic: ITopicStoreState;
}
