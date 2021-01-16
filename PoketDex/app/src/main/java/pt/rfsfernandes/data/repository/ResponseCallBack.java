package pt.rfsfernandes.data.repository;

/**
 * Interface ResponseCallBack created at 1/16/21 15:54 for the project PoketDex
 * By: rodrigofernandes
 */
public interface ResponseCallBack<T>{
  void onSuccess(T response);
  void onFailure(String errorMessage);
}
