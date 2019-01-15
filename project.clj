(defproject patcher "0.1.1"
  :description "Use REST inside your Clojure programs. Represent a patch to a data structure as a data structure and apply it."
  :url "https://github.com/vadim0x60/patcher"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies []
  :plugins [[lein-cloverage "1.0.13"]
            [lein-shell "0.5.0"]
            [lein-ancient "0.6.15"]
            [lein-changelog "0.3.2"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.9.0"]]}}
  :deploy-repositories [["releases" :clojars]]
  :aliases {"update-readme-version" ["shell" "sed" "-i" "s/\\\\[patcher \"[0-9.]*\"\\\\]/[patcher \"${:version}\"]/" "README.md"]}
  :release-tasks [["shell" "git" "diff" "--exit-code"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["changelog" "release"]
                  ["update-readme-version"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ["deploy"]
                  ["vcs" "push"]])
