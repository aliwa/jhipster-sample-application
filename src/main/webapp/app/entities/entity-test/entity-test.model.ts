import * as dayjs from 'dayjs';

export interface IEntityTest {
  id?: number;
  aString?: string;
  aInteger?: number;
  aLong?: number;
  aBigDecimal?: number;
  aFloat?: number;
  aDouble?: number;
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
  aImageBlobContentType?: string;
  aImageBlob?: string;
  aTextBlob?: string;
}

export class EntityTest implements IEntityTest {
  constructor(
    public id?: number,
    public aString?: string,
    public aInteger?: number,
    public aLong?: number,
    public aBigDecimal?: number,
    public aFloat?: number,
    public aDouble?: number,
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
    public aImageBlobContentType?: string,
    public aImageBlob?: string,
    public aTextBlob?: string
  ) {
    this.aBoolean = this.aBoolean ?? false;
  }
}

export function getEntityTestIdentifier(entityTest: IEntityTest): number | undefined {
  return entityTest.id;
}
