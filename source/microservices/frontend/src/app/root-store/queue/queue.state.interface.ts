import {Queue} from "../../api/models/queue";

export const QUEUE_FEATURE_KEY = 'queue';

export interface QueueStoreState {
  queueArray?: Queue[];
  error?: any;
  loading: boolean;
}

export const initialState: QueueStoreState = {
  loading: false
};
