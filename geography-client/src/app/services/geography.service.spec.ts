import { TestBed } from '@angular/core/testing';

import { GeographyService } from './geography.service';

describe('GeographyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GeographyService = TestBed.get(GeographyService);
    expect(service).toBeTruthy();
  });
});
