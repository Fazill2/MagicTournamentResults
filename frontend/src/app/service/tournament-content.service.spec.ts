import { TestBed } from '@angular/core/testing';

import { TournamentContentService } from './tournament-content.service';

describe('TournamentContentService', () => {
  let service: TournamentContentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TournamentContentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
