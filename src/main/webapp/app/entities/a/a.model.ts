import * as dayjs from 'dayjs';
import { AEnum } from 'app/entities/enumerations/a-enum.model';

export interface IA {
  id?: number;
  aString?: string;
  aInteger?: number;
  aLong?: number;
  aBigDecimal?: number;
  aFloat?: number;
  aDouble?: number;
  aEnum?: AEnum;
  aBoolean?: boolean;
  aLocalDate?: dayjs.Dayjs;
  aZonedDateTime?: dayjs.Dayjs;
  aInstant?: dayjs.Dayjs;
  aDuration?: string;
  aUUID?: string;
  aBlobContentType?: string;
  aBlob?: string;
  aAnyBlobContentType?: string;
  aAnyBlob?: string;
  imageBlobContentType?: string;
  imageBlob?: string;
  aTextBlob?: string;
}

export class A implements IA {
  constructor(
    public id?: number,
    public aString?: string,
    public aInteger?: number,
    public aLong?: number,
    public aBigDecimal?: number,
    public aFloat?: number,
    public aDouble?: number,
    public aEnum?: AEnum,
    public aBoolean?: boolean,
    public aLocalDate?: dayjs.Dayjs,
    public aZonedDateTime?: dayjs.Dayjs,
    public aInstant?: dayjs.Dayjs,
    public aDuration?: string,
    public aUUID?: string,
    public aBlobContentType?: string,
    public aBlob?: string,
    public aAnyBlobContentType?: string,
    public aAnyBlob?: string,
    public imageBlobContentType?: string,
    public imageBlob?: string,
    public aTextBlob?: string
  ) {
    this.aBoolean = this.aBoolean ?? false;
  }
}

export function getAIdentifier(a: IA): number | undefined {
  return a.id;
}
