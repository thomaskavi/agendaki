package com.thomaskavi.agendaki.projections;

public interface UserDetailsProjection {

  String getUsername();

  String getPassword();

  Long getRoleId();

  String getAuthority();
}