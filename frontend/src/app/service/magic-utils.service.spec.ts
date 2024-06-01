import { TestBed } from '@angular/core/testing';

import { MagicUtilsService } from './magic-utils.service';

describe('MagicUtilsService', () => {
  let service: MagicUtilsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MagicUtilsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
