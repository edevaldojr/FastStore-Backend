import { API_CONFIG } from "src/config/api.config";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Product } from "../models/product";

@Injectable()
export class ProductService {

  constructor(public http: HttpClient) {
  }

  findAll() : Observable<Product[]> {
    return this.http.get<Product[]>(`${API_CONFIG.baseUrl}/products/all`);
  }

  findById(productId: number) : Observable<Product[]> {
    return this.http.get<Product[]>(`${API_CONFIG.baseUrl}/products/`+ productId);
  }

}
