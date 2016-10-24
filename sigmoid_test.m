%% sigmoid test
close all;
xs = linspace(0, 1, 200);

% https://en.wikipedia.org/wiki/Logistic_function
x0 = 0.5; % x0 = x-value of sigmoids midpoint
k = 12; % k = steepness of the curve, 12 seesm to give a good result
lin = 0.5; % linearity vs sigmoidality

sigs = 1.0 ./(1.0 + exp(-k.*(xs - x0)));
activation = (1-lin) .* sigs +  lin .* xs;
plot(xs, activation); hold on
plot([0 0], [0 1]);
plot([1 1], [0 1]); hold off
xlim([-0.2, 1.2])