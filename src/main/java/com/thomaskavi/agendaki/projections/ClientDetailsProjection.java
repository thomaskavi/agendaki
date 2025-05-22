package com.thomaskavi.agendaki.projections;

public interface ClientDetailsProjection {

  String getUsername();

  String getPassword();

  Long getRoleId();

  String getAuthority();
}