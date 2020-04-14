import { TestBed } from '@angular/core/testing';

import { AutzService } from './autz.service';

describe('AutzService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AutzService = TestBed.get(AutzService);
    expect(service).toBeTruthy();
  });
});
