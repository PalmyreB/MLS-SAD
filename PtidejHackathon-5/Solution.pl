/*
between(Low, High, _) :-
    Low > High,
    !,
    fail.
between(Low, High, Low).
between(Low, High, Val) :-
    Now is Low + 1,
    between(Now, High, Val).
*/


distinct([]).
distinct([X|Xs]) :- 
	not(member(X, Xs)),
	distinct(Xs).

solution(S, E, N, D, M, O, R, Y) :-  
    	digit(S), S>0, digit(E), digit(N),digit(D),
    	digit(M), M>0, digit(O), digit(R), digit(Y),
    1000*S + 100*E + 10*N + D + 1000*M + 100*O + 10*R + E =:= 10000*M + 1000*O + 100*N + 10*E + Y, distinct([S, E, N, D, M, O, R, Y]).
    
digit(0).
digit(1).
digit(2).
digit(3).
digit(4).
digit(5).
digit(6).
digit(7).
digit(8).
digit(9).

solution(S, E, N, D, M, O, R, Y).
    