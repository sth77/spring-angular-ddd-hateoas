export interface Link {
    href: string;
}

export interface Links {
    [rel: string]: Link;
}

export interface Resource {
    _links: Links;
}

export interface ProductionOrder {
    name: string;
    state: string;
}

export interface ProductionOrderResource extends ProductionOrder, Resource {

}